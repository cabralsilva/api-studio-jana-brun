export enum TypeOfValueEnum {
  INTEGER = 'Número',
  STRING = 'Texto',
  DATE = 'Data'
}

export const TypeOfValueList: {
    key: string;
    value: string;
}[] = Object.entries(TypeOfValueEnum).map(([key, value]) => ({ key, value }))

export type TypeOfValueType = keyof typeof TypeOfValueEnum

export const TypeOfValueEntries = Object.entries(TypeOfValueEnum)
