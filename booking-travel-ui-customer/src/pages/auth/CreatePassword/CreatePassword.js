import React, { useState } from "react";
import classNames from "classnames/bind";
import {
  Container,
  Grid,
  FormControl,
  Input,
  Button,
  createTheme,
  ThemeProvider,
} from "@mui/material";
import { Link } from "react-router-dom";

import styles from "./CreatePassword.module.scss";
import { themeCustomer } from "~/components/CustomerMaterial";
import config from "~/config";

const cx = classNames.bind(styles);

function CreatePassword(props) {
    const theme = createTheme(themeCustomer);
  const [password, setPassword] = useState("");

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Handle form submission here
  };
  return (
    <div className={cx("wrapper")}>
      <Container>
        <Grid container justifyContent={"center"}>
          <Grid container item md={7} justifyContent={"center"}>
            <form className={cx("form-vertify")} onSubmit={handleSubmit}>
              <FormControl className={cx("input")}>
                <div className={cx("form-header")}>
                  <h3 className={cx("label-header")}>
                    Vui lòng nhập mật khẩu
                  </h3>
                </div>
                
                <Input
                  size="large"
                  sx={{ fontSize: "1.6rem" }}
                  className={cx("verification-code")}
                  type="password"
                  value={password}
                  onChange={handlePasswordChange}
                />
              </FormControl>
              <ThemeProvider theme={theme}>
                {/* <Link to={config.routes.createPassword}> */}
                  <Button
                    className={cx("btn-vertify")}
                    size="medium"
                    type="submit"
                    variant="contained"
                    color="primary"
                    sx={{ fontSize: "1.6rem" }}
                  >
                    Xác nhận
                  </Button>
                {/* </Link> */}
              </ThemeProvider>
            </form>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}

export default CreatePassword;
