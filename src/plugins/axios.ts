import axios, { AxiosInstance } from 'axios'
import EventBus from '@/store/enventBus'
import store from '@/store/store'

const apiClient: AxiosInstance = axios.create({
  baseURL: 'https://api-studio-jana-brun.ue.r.appspot.com/',
  // baseURL: 'http://localhost:8082/',
  headers: {
    'Content-type': 'application/json'
  }
})

const requestHandler = (request: any) => {
  request.headers.Authorization = store.state?.userDetails?.token
  return request
}

const responseHandler = (response: any) => {
  if (response.status >= 400 && response.status < 500) {
    EventBus.http4xx(response)
  }

  if (response.status >= 500 && response.status < 600) {
    EventBus.http4xx(response)
  }
  return response
}

const errorHandler = (error: any) => {
  if (error.response.status >= 400 && error.response.status < 500) {
    EventBus.http4xx(error.response)
  }
  return Promise.reject(error)
}

apiClient.interceptors.request.use(
  (request) => requestHandler(request),
  (error) => errorHandler(error)
)

apiClient.interceptors.response.use(
  (response) => responseHandler(response),
  (error) => errorHandler(error)
)

export default apiClient
