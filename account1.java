import java.util.concurrent.locks.*;

public class SavingsAccount {
    private double balance;
    private final Lock lock;
    private final Condition sufficientMoney;

    public SavingsAccount() {
        balance = 0;
        lock = new ReentrantLock();
        sufficientFunds = lock.newCondition();
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
            sufficientMoney.signalAll(); // signals the sufficientFunds condition whenever there is a deposit
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) throws InterruptedException {
        lock.lock();
        try {
            while (balance < amount) {
                sufficientMoney.await(); // waits until a deposit it made. Will wait again if balance not sufficient
            }
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }
}
