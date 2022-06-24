import { EmployeeStatusEnum } from './EmployeeStatusEnum'
import State from './State'
import { TypeOfPersonEnum } from './TypeOfPersonEnum'

type City = {
  id: number,
  name: string,
  state: State
}

export default City
