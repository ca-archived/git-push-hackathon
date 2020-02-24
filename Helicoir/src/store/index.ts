import { getAccessorType } from 'typed-vuex'
import { Modal } from '~/types/modal'

type Curation =
  | {}
  | {
      search?: {
        id: string
        title: string
        thumbnail: string
      }
      items?: [
        {
          id: string
          title: string
          thumbnail: string
        }
      ]
    }

type State = {
  authenticated: boolean
  token?: string
  curationItems?: Curation
}
type LocalStorage = {
  token?: string
  snapshot?: Curation
}

export const state = (): State => ({
  authenticated: false,
  token: '',
  curationItems: {}
})

export const mutations = {
  commitState(state: State, value: LocalStorage): void {
    state.token = value.token
    state.curationItems = value.snapshot
    localStorage.setItem('token', JSON.stringify(value.token))
    localStorage.setItem('snapshot', JSON.stringify(value.snapshot))
  },
  commitToken(state: State, value: string): void {
    state.token = value
    localStorage.setItem('token', JSON.stringify(value))
  },
  commitAuthenticated(state: State, value: boolean): void {
    state.authenticated = value
  },
  commitSnapshot(state: State, value: Curation): void {
    console.log('run commitSnapshot')
    console.log(value)
    state.curationItems = value
    console.log(state.curationItems)
    localStorage.setItem('snapshot', JSON.stringify(value))
  },
}

export const actions = {
  // 認証永続化とcuratedアイテムの一時保存（認証回復時に内容を保って復帰する）
  nuxtClientInit({ commit }: any, context: any): void {
    const tokenItem = JSON.parse(localStorage.getItem('token'))
    const snapshotItem = JSON.parse(localStorage.getItem('snapshot'))
    console.log(snapshotItem.search)
    const localstorageItems = {
      token: tokenItem,
      snapshot: snapshotItem
    }
    if (snapshotItem) {
      commit('commitState', localstorageItems)
    } else {
      commit('commitToken', tokenItem)
    }
  }
}

export const accessorType: State = getAccessorType({
  state,
  mutations,
  actions
})
