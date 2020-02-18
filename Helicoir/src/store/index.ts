import { getAccessorType } from 'typed-vuex'

type State = {
  token?: string
  // temporarilySaved?: {}
}

export const state = (): State => ({
  token: '',
  //temporarilySaved: {}
})

export const mutations = {
  commitToken(state: State, value: string) {
    state.token = value
  }
}

export const accessorType: State = getAccessorType({
  state,
  mutations
})
