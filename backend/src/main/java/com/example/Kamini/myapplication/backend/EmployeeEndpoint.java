package com.example.Kamini.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "employeeApi",
        version = "v1",
        resource = "employee",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Kamini.example.com",
                ownerName = "backend.myapplication.Kamini.example.com",
                packagePath = ""
        )
)
public class EmployeeEndpoint {

    private static final Logger logger = Logger.getLogger(EmployeeEndpoint.class.getName());

    /**
     * This method gets the <code>Employee</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Employee</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getEmployee")
    public Employee getEmployee(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getEmployee method");
        return null;
    }

    /**
     * This inserts a new <code>Employee</code> object.
     *
     * @param employee The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertEmployee")
    public Employee insertEmployee(Employee employee) {
        // TODO: Implement this function
        logger.info("Calling insertEmployee method");
        return employee;
    }
}