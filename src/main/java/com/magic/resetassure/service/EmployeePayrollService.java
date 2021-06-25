package com.magic.resetassure.service;

import com.magic.resetassure.entity.EmployeePayroll;

import java.util.List;

public class EmployeePayrollService {
    private List<EmployeePayroll> employeePayrolls;
    public EmployeePayrollService(List<EmployeePayroll> employeePayrolls) {
        this.employeePayrolls=employeePayrolls;
    }

    public void addEmployeeToPayroll(EmployeePayroll employeePayroll) {
        this.employeePayrolls.add(employeePayroll);
        System.out.println(employeePayroll.getName());
    }

    public long countEntries() {
        return this.employeePayrolls.stream().count();
    }
}
