import { getAccessorType } from 'typed-vuex'

type Curation = {
  search:
    | {}
    | {
        id: string
        title: string
        thumbnail: string
      }
  items:
    | []
    | [
        {
          id: string
          title: string
          thumbnail: string
        }
      ]
}

type State = {
  token: string
  curationItems: Curation
}
type LocalStorage = {
  token: string
  snapshot: Curation
}

export const state = (): State => ({
  token: '',
  curationItems: {
    search: {},
    items: [
      {
        id: 'no-items',
        title: 'no-items',
        thumbnail: 'no-items'
      }
    ]
  }
})

export const mutations = {
  commitStateSync(state: State, value: LocalStorage): void {
    state.token = value.token
    state.curationItems = value.snapshot
    localStorage.setItem('token', JSON.stringify(value.token))
    localStorage.setItem('snapshot', JSON.stringify(value.snapshot))
  },
  commitToken(state: State, value: string): void {
    state.token = value
    localStorage.setItem('token', JSON.stringify(value))
  },
  commitSnapshot(state: State, value: Curation): void {
    state.curationItems = value
    localStorage.setItem('snapshot', JSON.stringify(value))
  },
  commitItem(state: State, value: any): void {
    state.curationItems.items[0].id === 'no-items'
      ? (state.curationItems.items.splice(0, 1))
      : {}
    state.curationItems.items.push(value)
  },
  commitSearch(state: State, value: any) {
    state.curationItems.search = value
    localStorage.setItem('snapshot', JSON.stringify(state.curationItems))
  }
}

export const actions = {
  // 認証永続化とcuratedアイテムの一時保存（認証回復時に内容を保って復帰する）
  nuxtClientInit({ commit }: any, context: any): void {
    const tokenItem = context.route.hash
      ? context.route.hash.slice(14, context.route.hash.indexOf('&'))
      : JSON.parse(localStorage.getItem('token'))
    const snapshotItem = JSON.parse(localStorage.getItem('snapshot'))
    const localstorageItems = {
      token: tokenItem,
      snapshot: snapshotItem
    }
    if (snapshotItem) {
      commit('commitStateSync', localstorageItems)
    } else {
      commit('commitToken', tokenItem)
      localStorage.setItem(
        'snapshot',
        JSON.stringify(context.store.state.curationItems)
      )
    }
  }
}

export const accessorType: State = getAccessorType({
  state,
  mutations,
  actions
})
