export enum ProductCategoryEnum {
  LESSON = 'Aulas',
  TICKETS = 'Ingressos',
  OTHER = 'Outros'
}

export const ProductCategoryList: {
    key: string;
    value: string;
}[] = Object.entries(ProductCategoryEnum).map(([key, value]) => ({ key, value }))

export type ProductCategoryType = keyof typeof ProductCategoryEnum

export const ProductCategoryEntries = Object.entries(ProductCategoryEnum)
