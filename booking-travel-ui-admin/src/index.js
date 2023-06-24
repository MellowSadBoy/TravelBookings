import React from "react";
import ReactDOM from "react-dom";
import "assets/css/App.css";
import { ChakraProvider } from "@chakra-ui/react";
import theme from "theme/theme";
import AppProvider from "store/Provider";
import "react-toastify/dist/ReactToastify.css";
import App from "app";
ReactDOM.render(
  <ChakraProvider theme={theme}>
    <React.StrictMode>
      <AppProvider>
        <App />
      </AppProvider>
    </React.StrictMode>
  </ChakraProvider>,
  document.getElementById("root")
);
