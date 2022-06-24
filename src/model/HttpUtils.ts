export interface HttpSearchRequest {
  globalFilter: string
  columnList: string[]
  sizePage: number
  currentPage: number
  orderColumn: string
  asc: boolean
  resultUnique: boolean
  example: unknown
  pageable: boolean
}

export interface HttpSearchResponse<T = unknown> {
  result: T[]
  globalFilter: string
  sizePage: number
  currentPage: number
  orderColumn: string[]
  asc: boolean
  total: number
  last: boolean
}
