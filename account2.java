import java.util.concurrent.locks.*;

public class SavingsAccount {
    private double balance;
    private final Lock lock;
    private final Condition sufficientMoney;
    private int preferredInt;

    public SavingsAccount() {
        balance = 0;
        lock = new ReentrantLock();
        sufficientFunds = lock.newCondition();
        preferredInt = 0
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

    public void owithdraw(double amount) throws InterruptedException {
        lock.lock();
        try {
            while (balance < amount || preferredInt > 0) {
                sufficientMoney.await(); // waits until a deposit it made. Will wait again if balance not sufficient
            }
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }
  
      public void pwithdraw(double amount) throws InterruptedException {
        lock.lock();
        try {
            preferredInt++;
            while (balance < amount) {
                sufficientMoney.await(); // waits until a deposit it made. Will wait again if balance not sufficient
            }
            balance -= amount;
            preferredInt--;
        } finally {
            lock.unlock();
        }
    }
}
