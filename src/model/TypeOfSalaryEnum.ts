export enum TypeOfSalaryEnum {
    BY_HOUR = 'Por hora',
    BY_PERCENT = 'Por percentual',
    BY_MONTH = 'Por mês'
}

export const TypeOfSalaryList: {
    key: string;
    value: string;
}[] = Object.entries(TypeOfSalaryEnum).map(([key, value]) => ({ key, value }))
