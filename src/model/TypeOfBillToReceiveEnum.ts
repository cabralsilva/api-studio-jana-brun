export enum TypeOfBillToReceiveEnum {
  SINGLE = 'Avulso',
  LESSON_DEMO = 'Aula experimental',
  LESSON_SINGLE = 'Aula avulsa',
  MATRICULATION = 'Matrículas',
  PRESENTATION = 'Apresentações',
  SALE = 'Vendas'
}

export const TypeOfBillToReceiveList: {
    key: string;
    value: string;
}[] = Object.entries(TypeOfBillToReceiveEnum).map(([key, value]) => ({ key, value }))

export type TypeOfBillToReceiveType = keyof typeof TypeOfBillToReceiveEnum

export const TypeOfBillToReceiveEntries = Object.entries(TypeOfBillToReceiveEnum)
