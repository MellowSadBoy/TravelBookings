import { configureStore } from "@reduxjs/toolkit";
import { Provider } from "react-redux";
import authSlice from "./AuthSlice/AuthSlice";
import globalSlice from "./GlobalSlice/GlobalSlice";

const store = configureStore({
  reducer: {
    auth: authSlice,
    global: globalSlice,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

function AppProvider({ children }) {
  return <Provider store={store}>{children}</Provider>;
}

export default AppProvider;
