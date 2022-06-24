export enum EmployeeStatusEnum {
    IN_COMPANY = 'Ativo',
    LEFT_COMPANY = 'Inativo'
}

export const EmployeeStatusList: {
    key: string;
    value: string;
}[] = Object.entries(EmployeeStatusEnum).map(([key, value]) => ({ key, value }))

export type EmployeeStatusType = keyof typeof EmployeeStatusEnum
