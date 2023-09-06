import com.example.playtox.model.Account;
import repository.AccountRepository;
import service.AccountWorker;
import service.TransferService;

public class Start {
    public static void main(String[] args) {
        Account a1 = new Account();
        Account a2 = new Account();
        Account a3 = new Account();
        Account a4 = new Account();

        AccountRepository repository = new AccountRepository();
        repository.addAccount(a1);
        repository.addAccount(a2);
        repository.addAccount(a3);
        repository.addAccount(a4);

        TransferService transferService = new TransferService(repository);
        AccountWorker worker = new AccountWorker(a1, a2, transferService);
        AccountWorker worker2 = new AccountWorker(a3, a4, transferService);

        worker.start();
        worker2.start();
    }
}
