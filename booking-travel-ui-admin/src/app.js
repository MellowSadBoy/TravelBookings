import { ThemeEditorProvider } from "@hypertheme-editor/chakra-ui";
import React, { useEffect, useState } from "react";
import { Redirect, Route } from "react-router-dom/cjs/react-router-dom";
import { HashRouter, Switch } from "react-router-dom/cjs/react-router-dom.min";
import { ToastContainer } from "react-toastify";
import AuthLayout from "layouts/auth";
import AdminLayout from "layouts/admin";
import RTLLayout from "layouts/rtl";
import { Box } from "@chakra-ui/react";
import Loading from "components/loading/Loading";
import { useDispatch } from "react-redux";
import httpServ from "service/httpService";
import { setInfoCompany } from "store/GlobalSlice/GlobalSlice";
const App = () => {
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    httpServ.getInfoCompany().then((res) => {
      dispatch(setInfoCompany(res.data));
      setLoading(false);
    });
  }, [dispatch]);

  if (loading) {
    return (
      <Box
        w="100vw"
        display="flex"
        justifyContent="center"
        alignItems="center"
        h="100vh"
      >
        <Loading />
      </Box>
    );
  }

  return (
    <>
      <ThemeEditorProvider>
        <HashRouter>
          <Switch>
            <Route path={`/auth`} component={AuthLayout} />
            <Route path={`/admin`} component={AdminLayout} />
            <Route path={`/rtl`} component={RTLLayout} />
            <Redirect from="/" to="/admin" />
          </Switch>
        </HashRouter>
        <ToastContainer />
      </ThemeEditorProvider>
    </>
  );
};

export default App;
