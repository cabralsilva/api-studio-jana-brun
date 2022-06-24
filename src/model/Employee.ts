import { EmployeeStatusEnum } from './EmployeeStatusEnum'
import Person from './Person'

type Employee = {
    id:number,
    salaryValue: number,
    status: EmployeeStatusEnum,
    medicinContinuous: boolean,
    allergiesContinuous: boolean,
    person: Person
}

export default Employee
