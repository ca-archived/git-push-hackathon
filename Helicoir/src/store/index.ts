import { getAccessorType } from 'typed-vuex'

type Curated =
  | []
  | [
      {
        id: string
        title: string
        thumbnail: string
      }
    ]

type State = {
  authenticated: boolean
  token?: string
  curatedItem: Curated
}

export const state = (): State => ({
  authenticated: false,
  token: '',
  curatedItem: [{ id: 'test', title: 'test', thumbnail: 'test' }]
})

export const mutations = {
  commitLocalStorage(state: State, value: State): void {
    state = value
    localStorage.setItem('token', JSON.stringify(value.token))
    localStorage.setItem('snapshot', JSON.stringify(value.curatedItem))
  },
  commitToken(state: State, value: string): void {
    state.token = value
    localStorage.setItem('token', JSON.stringify(value))
  },
  commitAuthenticated(state: State, value: boolean): void {
    state.authenticated = value
  },
  commitSnapshot(state: State, value: Curated): void {
    state.curatedItem = value
  }
}

export const actions = {
  // 認証永続化とcuratedアイテムの一時保存（認証回復時に内容を保って復帰する）
  nuxtClientInit({ commit }: any, context: any): void {
    const tokenItem = JSON.parse(localStorage.getItem('token'))
    const snapshotItem = JSON.parse(localStorage.getItem('snapshot'))
    const localstorageItems = {
      token: tokenItem,
      snapshot: snapshotItem
    }
    commit('commitLocalStorage', localstorageItems)
  }
}

export const accessorType: State = getAccessorType({
  state,
  mutations
})
