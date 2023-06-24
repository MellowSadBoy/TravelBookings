import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import localStorage from "../../service/localStorage";

const initialState = {
  token: localStorage.token.get(),
  user: localStorage.userInfo.get(),
  activeAccounts:
    localStorage.activeAccount.get() !== null
      ? localStorage.activeAccount.get()
      : [],
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setToken: (state, action) => {
      state.token = action.payload;
      localStorage.token.set(action.payload);
    },
    setUser: (state, action) => {
      state.user = action.payload;
      localStorage.userInfo.set(action.payload);
    },
    handleLogout: (state, action) => {
      state.user = null;
      state.token = null;
      localStorage.userInfo.remove();
      localStorage.token.remove();
    },
    addActiveAccounts: (state, action) => {
      const checkId = state.activeAccounts.findIndex(
        (vl) => vl.userId === action.payload.userId
      );
      if (checkId === -1) {
        state.activeAccounts.push(action.payload);
        localStorage.activeAccount.set(state.activeAccounts);
      }
    },
  },
});

export const { setToken, setUser, handleLogout, addActiveAccounts } =
  authSlice.actions;

export default authSlice.reducer;
