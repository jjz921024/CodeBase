package gdut;

import gdut.pojo.Employee;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 业务层  Spring-tx demo
 * Created by Jun on 2018/2/14.
 */
public class Service {
    private EmpDao empDao;
    private TransactionTemplate transactionTemplate;

    public void setEmpDao(EmpDao empDao) {
        this.empDao = empDao;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void doService(Employee employee1, Employee employee2) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                empDao.createEmp(employee1);
                //int i = 1 / 0;
                empDao.createEmp(employee2);
            }
        });
    }

    // 代理了此方法
    public void doTxProxyService(Employee employee1, Employee employee2) {
        empDao.createEmp(employee1);
        //int i = 1 / 0;
        empDao.createEmp(employee2);
    }

    // xml中对这个方法进行了代理
    public void doTxAOPService(Employee employee1, Employee employee2) {
        empDao.createEmp(employee1);
        //int i = 1 / 0;
        empDao.createEmp(employee2);
    }
}
