package bit.project.server.controller;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import bit.project.server.UsecaseList;
import bit.project.server.entity.User;
import bit.project.server.entity.Employee;
import bit.project.server.dao.EmployeeDao;
import org.springframework.http.HttpStatus;
import jakarta.persistence.RollbackException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpServletRequest;
import bit.project.server.util.dto.PageQuery;
import bit.project.server.util.dto.ResourceLink;
import bit.project.server.entity.Employeestatus;
import org.springframework.web.bind.annotation.*;
import bit.project.server.util.helper.PageHelper;
import org.springframework.data.domain.PageRequest;
import bit.project.server.util.helper.PersistHelper;
import bit.project.server.util.helper.CodeGenerator;
import bit.project.server.util.validation.EntityValidator;
import bit.project.server.util.exception.ConflictException;
import bit.project.server.util.validation.ValidationErrorBag;
import bit.project.server.util.security.AccessControlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import bit.project.server.util.exception.DataValidationException;
import bit.project.server.util.exception.ObjectNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("/employees")
public class EmployeeController{

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private AccessControlManager accessControlManager;

    @Autowired
    private CodeGenerator codeGenerator;

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "tocreation");
    private final CodeGenerator.CodeGeneratorConfig codeConfig;

    public EmployeeController(){
        codeConfig = new CodeGenerator.CodeGeneratorConfig("employee");
        codeConfig.setColumnName("code");
        codeConfig.setLength(8);
        codeConfig.setPrefix("EM");
        codeConfig.setYearlyRenew(true);
    }

    @GetMapping
    public Page<Employee> getAll(PageQuery pageQuery, HttpServletRequest request) {
        accessControlManager.authorize(request, "No privilege to get all employees", UsecaseList.SHOW_ALL_EMPLOYEES);

        if(pageQuery.isEmptySearch()){
            return employeeDao.findAll(PageRequest.of(pageQuery.getPage(), pageQuery.getSize(), DEFAULT_SORT));
        }

        String code = pageQuery.getSearchParam("code");
        String callingname = pageQuery.getSearchParam("callingname");
        String nic = pageQuery.getSearchParam("nic");
        Integer employeestatusId = pageQuery.getSearchParamAsInteger("employeestatus");

        List<Employee> employees = employeeDao.findAll(DEFAULT_SORT);
        Stream<Employee> stream = employees.parallelStream();

        List<Employee> filteredEmployees = stream.filter(employee -> {
            if(code!=null)
                if(!employee.getCode().toLowerCase().contains(code.toLowerCase())) return false;
            if(callingname!=null)
                if(!employee.getCallingname().toLowerCase().contains(callingname.toLowerCase())) return false;
            if(nic!=null)
                if(!employee.getNic().toLowerCase().contains(nic.toLowerCase())) return false;
            if(employeestatusId!=null)
                if(!employee.getEmployeestatus().getId().equals(employeestatusId)) return false;
            return true;
        }).collect(Collectors.toList());

        return PageHelper.getAsPage(filteredEmployees, pageQuery.getPage(), pageQuery.getSize());

    }

    @GetMapping("/basic")
    public Page<Employee> getAllBasic(PageQuery pageQuery, HttpServletRequest request){
        accessControlManager.authorize(request, "No privilege to get all employees' basic data", UsecaseList.SHOW_ALL_EMPLOYEES);
        return employeeDao.findAllBasic(PageRequest.of(pageQuery.getPage(), pageQuery.getSize(), DEFAULT_SORT));
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable Integer id, HttpServletRequest request) {
        accessControlManager.authorize(request, "No privilege to get employee", UsecaseList.SHOW_EMPLOYEE_DETAILS, UsecaseList.UPDATE_EMPLOYEE);
        Optional<Employee> optionalEmployee = employeeDao.findById(id);
        if(optionalEmployee.isEmpty()) throw new ObjectNotFoundException("Employee not found");
        return optionalEmployee.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id, HttpServletRequest request){
        accessControlManager.authorize(request, "No privilege to delete employees", UsecaseList.DELETE_EMPLOYEE);

        try{
            if(employeeDao.existsById(id)) employeeDao.deleteById(id);
        }catch (DataIntegrityViolationException | RollbackException e){
            throw new ConflictException("Cannot delete. Because this employee already used in another module");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceLink add(@RequestBody Employee employee, HttpServletRequest request) throws InterruptedException {
        User authUser = accessControlManager.authorize(request, "No privilege to add new employee", UsecaseList.ADD_EMPLOYEE);

        employee.setTocreation(LocalDateTime.now());
        employee.setCreator(authUser);
        employee.setId(null);
        employee.setEmployeestatus(new Employeestatus(1));;


        EntityValidator.validate(employee);

        ValidationErrorBag errorBag = new ValidationErrorBag();

        if(employee.getNic() != null){
            Employee employeeByNic = employeeDao.findByNic(employee.getNic());
            if(employeeByNic!=null) errorBag.add("nic","nic already exists");
        }

        if(employee.getMobile() != null){
            Employee employeeByMobile = employeeDao.findByMobile(employee.getMobile());
            if(employeeByMobile!=null) errorBag.add("mobile","mobile already exists");
        }

        if(employee.getEmail() != null){
            Employee employeeByEmail = employeeDao.findByEmail(employee.getEmail());
            if(employeeByEmail!=null) errorBag.add("email","email already exists");
        }

        if(errorBag.count()>0) throw new DataValidationException(errorBag);

        PersistHelper.save(()->{
            employee.setCode(codeGenerator.getNextId(codeConfig));
            return employeeDao.save(employee);
        });

        return new ResourceLink(employee.getId(), "/employees/"+employee.getId());
    }

    @PutMapping("/{id}")
    public ResourceLink update(@PathVariable Integer id, @RequestBody Employee employee, HttpServletRequest request) {
        accessControlManager.authorize(request, "No privilege to update employee details", UsecaseList.UPDATE_EMPLOYEE);

        Optional<Employee> optionalEmployee = employeeDao.findById(id);
        if(optionalEmployee.isEmpty()) throw new ObjectNotFoundException("Employee not found");
        Employee oldEmployee = optionalEmployee.get();

        employee.setId(id);
        employee.setCode(oldEmployee.getCode());
        employee.setCreator(oldEmployee.getCreator());
        employee.setTocreation(oldEmployee.getTocreation());


        EntityValidator.validate(employee);

        ValidationErrorBag errorBag = new ValidationErrorBag();

        if(employee.getNic() != null){
            Employee employeeByNic = employeeDao.findByNic(employee.getNic());
            if(employeeByNic!=null)
                if(!employeeByNic.getId().equals(id))
                    errorBag.add("nic","nic already exists");
        }

        if(employee.getMobile() != null){
            Employee employeeByMobile = employeeDao.findByMobile(employee.getMobile());
            if(employeeByMobile!=null)
                if(!employeeByMobile.getId().equals(id))
                    errorBag.add("mobile","mobile already exists");
        }

        if(employee.getEmail() != null){
            Employee employeeByEmail = employeeDao.findByEmail(employee.getEmail());
            if(employeeByEmail!=null)
                if(!employeeByEmail.getId().equals(id))
                    errorBag.add("email","email already exists");
        }

        if(errorBag.count()>0) throw new DataValidationException(errorBag);

        employee = employeeDao.save(employee);
        return new ResourceLink(employee.getId(), "/employees/"+employee.getId());
    }

}