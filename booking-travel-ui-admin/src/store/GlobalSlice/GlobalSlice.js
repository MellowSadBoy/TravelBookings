import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  infoCompany: {},
};

export const globalSlice = createSlice({
  name: "global",
  initialState,
  reducers: {
    setInfoCompany: (state, action) => {
      state.infoCompany = action.payload;
    },
  },
});

export const { setInfoCompany } = globalSlice.actions;

export default globalSlice.reducer;
