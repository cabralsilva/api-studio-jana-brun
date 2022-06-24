export enum OftenEnum {
  ONCE = 'Único',
  DAILY = 'Diário',
  WEEKLY = 'Semanal',
  MONTHLY = 'Mensal',
  YEARLY = 'Anual'
}

export const OftenList: {
    key: string;
    value: string;
}[] = Object.entries(OftenEnum).map(([key, value]) => ({ key, value }))

export type OftenType = keyof typeof OftenEnum

export const OftenEntries = Object.entries(OftenEnum)
