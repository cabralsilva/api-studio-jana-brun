import Country from './Country'
import { EmployeeStatusEnum } from './EmployeeStatusEnum'
import { TypeOfPersonEnum } from './TypeOfPersonEnum'

type State = {
  id: number,
  name: string,
  country: Country
}

export default State
