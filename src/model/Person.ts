import Address from './Address'
import { EmployeeStatusEnum } from './EmployeeStatusEnum'
import { TypeOfPersonEnum } from './TypeOfPersonEnum'

type Person = {
    id: number,
    name: string,
    type: TypeOfPersonEnum,
    bornDate: Date,
    nickName: string,
    address: Address
}

export default Person
