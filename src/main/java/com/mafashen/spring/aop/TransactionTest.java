package com.mafashen.spring.aop;

import com.mafashen.spring.mybatis.DAO.BookDAO;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author mafashen
 * @since 2020-01-10.
 */
@Service("")
public class TransactionTest {

    @Autowired
    private BookDAO bookDAO;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        TransactionTest transactionTest = ctx.getBean("transactionTest", TransactionTest.class);
        transactionTest.doTransactionA();
    }

    @Transactional(rollbackFor = Exception.class)
    public void doTransactionA(){
        bookDAO.updatePrice();
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                System.out.println("doTransactionA#afterCommit");
                TransactionTest test = (TransactionTest)AopContext.currentProxy();
                test.doTransactionB();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void doTransactionB(){
        bookDAO.updatePrice();

        throw new RuntimeException("test");
//        try {
//            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                @Override
//                public void afterCommit() {
//                    System.out.println("doTransactionB#afterCommit");
//                }
//            });
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
    }
}

