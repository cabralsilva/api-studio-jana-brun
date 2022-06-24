export enum TypeOfPersonEnum {
    INDIVIDUAL = 'Física',
    CORPORATE = 'Jurídica'
}

export const TypeOfPersonList: {
    key: string;
    value: string;
}[] = Object.entries(TypeOfPersonEnum).map(([key, value]) => ({ key, value }))

export const TypeOfPerson = Object.entries(TypeOfPersonEnum)
