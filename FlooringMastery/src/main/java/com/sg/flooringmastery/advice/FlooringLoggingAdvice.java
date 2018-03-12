package com.sg.flooringmastery.advice;

import com.sg.flooringmastery.dao.FlooringAuditDao;
import com.sg.flooringmastery.dao.FlooringDaoException;
import org.aspectj.lang.JoinPoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jswan
 */
public class FlooringLoggingAdvice {

        FlooringAuditDao auditDao;

    public FlooringLoggingAdvice(FlooringAuditDao auditDao) {
            this.auditDao = auditDao;
        }

        public void createAuditEntry(JoinPoint jp) {
            Object[] args = jp.getArgs();
            String auditEntry = jp.getSignature().getName() + ": ";
            for (Object currentArg : args) {
                auditEntry += currentArg;
            }
            try {
                auditDao.writeAuditEntry(auditEntry);
            } catch (FlooringDaoException e) {
                System.err.println(
                        "ERROR: Could not create audit entry in LoggingAdvice.");
            }
        }
}
