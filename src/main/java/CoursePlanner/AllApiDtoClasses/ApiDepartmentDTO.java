package CoursePlanner.AllApiDtoClasses;

import CoursePlanner.Model.Department;

public class ApiDepartmentDTO {
    public long deptId;
    public String name;

    public ApiDepartmentDTO(Department department) {
        this.deptId = department.getDeptId();
        this.name = department.getName();
    }
}
