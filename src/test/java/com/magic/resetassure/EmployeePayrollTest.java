package com.magic.resetassure;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.magic.resetassure.entity.EmployeePayroll;
import com.magic.resetassure.service.EmployeePayrollService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeePayrollTest {
    @BeforeEach
     void beforeAll() {
        RestAssured.baseURI="http://localhost";
        RestAssured.port=3000;
    }
    public EmployeePayroll[] getEmployeeList(){
        Response response=RestAssured.get("/employees");
        System.out.println(response.asString());
        EmployeePayroll[] arrayOfEmployee=new Gson().fromJson(response.asString(),EmployeePayroll[].class);
        return arrayOfEmployee;
    }
    public Response addEmployeeToJsonServer(EmployeePayroll employeePayrolls){
        String empJson = new Gson().toJson(employeePayrolls);
        RequestSpecification request=RestAssured.given();
        request.header("Content-Type","application/json");
        request.body(empJson);
        return  request.post("/employees");
    }
    public Response addMultipleEmployeeToJsonServer(List<EmployeePayroll> list){
        String empJson=new Gson().toJson(list);
        RequestSpecification request=RestAssured.given();
        request.header("Content-Type","application/json");
        request.body(empJson);
        return  request.post("/employees");
    }
    public Response updateEmployeeInJsonServer(EmployeePayroll employeePayrolls){
        String empJson=new Gson().toJson(employeePayrolls);
        RequestSpecification request=RestAssured.given();
        request.header("Content-Type","application/json");
        request.body(empJson);
        return request.put("/employees/2");
    }
    public Response deleteEmployeeInJsonServer(){
        RequestSpecification request=RestAssured.given();
        request.header("Content-Type","application/json");
        return request.delete("/employees/2");
    }

    @Test
    void givenJsonFile_RetrieveDataFromFileAndStoreInObject_returnsCount() {
        EmployeePayroll[] arraysOfEmployees=getEmployeeList();
        EmployeePayrollService employeePayrollService=new EmployeePayrollService(Arrays.asList(arraysOfEmployees));
        long entries=employeePayrollService.countEntries();
        Assertions.assertEquals(2,entries);
    }

    @Test
    void givenEmployeeData_shouldAddinJsonFile_returnsTrueIfAdded() {
        EmployeePayroll employeePayroll=new EmployeePayroll
                ("Saumya",30000, LocalDate.of(2020,02,01),"male");
        Response response=addEmployeeToJsonServer(employeePayroll);
        int statusCode=response.getStatusCode();
        Assertions.assertEquals(200,statusCode);
        EmployeePayroll[] arraysOfEmployees=getEmployeeList();
        EmployeePayrollService employeePayrollService=new EmployeePayrollService(Arrays.asList(arraysOfEmployees));
        employeePayroll=new Gson().fromJson(response.asString(),EmployeePayroll.class);
        employeePayrollService.addEmployeeToPayroll(employeePayroll);
        long entries=employeePayrollService.countEntries();
        Assertions.assertEquals(2,entries);
    }

    @Test
    void givenMultipleEmployeeData_shouldAddInJsonFile_returnCountValues() {
        List<EmployeePayroll> list=new ArrayList<>();
        list.add(new EmployeePayroll("saumya",23000,LocalDate.of(2020,01,02),"female"));
        list.add(new EmployeePayroll("virat",230000,LocalDate.of(2020,03,01),"male"));
        Response response = addMultipleEmployeeToJsonServer(list);
        int statusCode=response.getStatusCode();
        Assertions.assertEquals(201,statusCode);
        EmployeePayroll[] arraysOfEmployees=getEmployeeList();
        EmployeePayrollService employeePayrollService=new EmployeePayrollService(Arrays.asList(arraysOfEmployees));
        long entries=employeePayrollService.countEntries();
        Assertions.assertEquals(4,entries);
    }

    @Test
    void givenUpdatedEmployeeData_shouldReturnUpdatedValues_returnsCountValues() {
        EmployeePayroll employeePayroll=new EmployeePayroll
                ("Saumya",30000, LocalDate.of(2020,02,01),"male");
        Response response=updateEmployeeInJsonServer(employeePayroll);
        int statusCode=response.getStatusCode();
        Assertions.assertEquals(200,statusCode);
        EmployeePayroll[] arraysOfEmployees=getEmployeeList();
        EmployeePayrollService employeePayrollService=new EmployeePayrollService(Arrays.asList(arraysOfEmployees));
        long entries=employeePayrollService.countEntries();
        Assertions.assertEquals(2,entries);
    }

    @Test
    void givenIdToBeDeleted_deletesTheID_returnTheNewCount() {
        deleteEmployeeInJsonServer();
        EmployeePayroll[] arraysOfEmployees=getEmployeeList();
        EmployeePayrollService employeePayrollService=new EmployeePayrollService(Arrays.asList(arraysOfEmployees));
        Assertions.assertEquals(1,employeePayrollService.countEntries());
    }
}
