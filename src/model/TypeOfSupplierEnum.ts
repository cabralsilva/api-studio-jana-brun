export enum TypeOfSupplierEnum {
  MERCHANDISE = 'Mercadoria',
  ELETRICITY = 'Eletricidade',
  WATER = 'Água',
  CARRYING = 'Transporte',
  RENT = 'Aluguel',
  OTHER = 'Outros'
}

export const TypeOfSupplierList: {
    key: string;
    value: string;
}[] = Object.entries(TypeOfSupplierEnum).map(([key, value]) => ({ key, value }))

export type TypeOfSupplierType = keyof typeof TypeOfSupplierEnum

export const TypeOfSupplierEntries = Object.entries(TypeOfSupplierEnum)
