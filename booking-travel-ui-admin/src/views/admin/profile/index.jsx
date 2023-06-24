import { Box, Grid } from "@chakra-ui/react";

// Custom components
import Banner from "views/admin/profile/components/Banner";

// Assets
import banner from "assets/img/auth/banner.png";
import avatar from "assets/img/avatars/avatar4.png";
import React, { useEffect, useState } from "react";
import Information from "./components/Information";
import { useDispatch, useSelector } from "react-redux";
import httpServ from "service/httpService";
import { setUser } from "store/AuthSlice/AuthSlice";
import Loading from "components/loading/Loading";

export default function Overview() {
  const token = useSelector((state) => state.auth.token);
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(true);
  const [isReload, setIsReload] = useState(true);

  useEffect(() => {
    if (!isReload || !token) return;
    setLoading(true);
    try {
      httpServ.getUserInfoByToken(token).then((res) => {
        const { data } = res;
        dispatch(setUser(data.data));
        setLoading(false);
        setIsReload(false);
      });
    } catch (error) {
      setLoading(false);
      setIsReload(false);
    }
  }, [token, isReload, dispatch]);
  if (loading) {
    return (
      <Box padding="200px 0">
        <Loading />;
      </Box>
    );
  }

  return (
    <Box pt={{ base: "130px", md: "80px", xl: "80px" }}>
      <Grid
        templateColumns={{
          base: "1fr",
          lg: "1.34fr 1fr 1.62fr",
        }}
        templateRows={{
          base: "repeat(3, 1fr)",
          lg: "1fr",
        }}
        gap={{ base: "20px", xl: "20px" }}
      >
        <Banner
          gridArea="1 / 1 / 2 / 2"
          banner={banner}
          setIsReload={setIsReload}
        />
        <Information
          gridArea={{ base: "2 / 1 / 3 / 2", lg: "1 / 2 / 9 / 5" }}
          token={token}
          setIsReload={setIsReload}
        />
      </Grid>
    </Box>
  );
}
