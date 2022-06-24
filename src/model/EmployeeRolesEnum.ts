export enum EmployeeRolesEnum {
  ROLE_ADMIN = 'Administrativo',
  ROLE_BASIC = 'Básico'
}

export const EmployeeRolesList: {
    key: string;
    value: string;
}[] = Object.entries(EmployeeRolesEnum).map(([key, value]) => ({ key, value }))

export type EmployeeRolesType = keyof typeof EmployeeRolesEnum

export const EmployeeRolesEntries = Object.entries(EmployeeRolesEnum)
