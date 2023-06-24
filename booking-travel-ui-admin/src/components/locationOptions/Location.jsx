import {
  FormControl,
  FormLabel,
  Select,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import React, { memo, useEffect, useState } from "react";
import {
  convertIdNameToString,
  getIdAndNameLocation,
} from "../../share/utils/functions";
import httpServ from "service/httpService";
import Loading from "components/loading/Loading";

const getProvinces = async () => {
  return new Promise((resolve, reject) => {
    httpServ
      .getProvinces()
      .then((res) => {
        const data = res.data;
        resolve(data);
      })
      .catch(() => reject([]));
  });
};

const getDistricts = async (id) => {
  return new Promise((resolve, reject) => {
    httpServ
      .getDistrictsByProvinceId(id)
      .then((res) => {
        const data = res.data;
        resolve(data);
      })
      .catch(() => reject([]));
  });
};

const getWards = async (id) => {
  return new Promise((resolve, reject) => {
    httpServ
      .getWardsByDistrictId(id)
      .then((res) => {
        const data = res.data;
        resolve(data);
      })
      .catch(() => reject([]));
  });
};

const Location = ({ onChange, defaultValues, isDisabled = false,showDefault=false }) => {
  const textColor = useColorModeValue("navy.700", "white");
  const brandStars = useColorModeValue("brand.500", "brand.400");
  const [provinces, setProvinces] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [wards, setWards] = useState([]);
  const [loading, setLoading] = useState(false);
  const [loadingDistrict, setLoadingDistrict] = useState(false);
  const [loadingWard, setLoadingWard] = useState(false);

  const [province, setProvince] = useState({
    code: "",
    name: "",
    child_id: null,
  });

  const [district, setDistrict] = useState({
    code: "",
    name: "",
    child_id: null,
  });

  const [ward, setWard] = useState({
    code: "",
    name: "",
  });

  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    let delaySendData = setTimeout(() => {
      onChange(province, district, ward);
    }, 500);
    return () => {
      clearTimeout(delaySendData);
    };
  }, [province, district, ward, onChange]);

  const updateProvinces = async (value) => {
    setProvince(value);
    updateDistricts(value);
  };

  const updateDistricts = async (value) => {
    setLoadingDistrict(true);
    console.log(value);
    const districsData = await getDistricts(value.child_id);
    const firstSelected = districsData[0];
    setLoadingDistrict(false);
    setDistrict({
      code: firstSelected.code,
      name: firstSelected.name,
      child_id: firstSelected.child_id,
    });
    setDistricts(districsData);
    updateWards({
      code: firstSelected.code,
      name: firstSelected.name,
      child_id: firstSelected.child_id,
    });
  };

  const updateWards = async (value) => {
    setLoadingWard(true);
    const wardsData = await getWards(value.child_id);
    const firstSelected = wardsData[0];
    setLoadingWard(false);
    setWard({
      code: firstSelected.code,
      name: firstSelected.name,
      child_id: firstSelected.child_id,
    });
    setWards(wardsData);
  };

  useEffect(() => {
    if (mounted ) return;
    async function setDefault() {
      const {
        district,
        districtCode,
        province_child_id,
        district_child_id,
        province,
        provinceCode,
        ward,
        wardCode,
      } = defaultValues;
      if(!province_child_id || !district_child_id){
        setDefaultBase();
        return;
      }
      const provincesData = await getProvinces();
      const districtsData = await getDistricts(province_child_id);
      const wardsData = await getWards(district_child_id);
      setProvinces(provincesData);
      setDistricts(districtsData);
      setWards(wardsData);
      province &&
      provinceCode &&
      setProvince({
        name: province,
        code: provinceCode,
        child_id: province_child_id,
      });
      district &&
        districtCode &&
      setDistrict({
          name: district,
          code: districtCode,
          child_id: district_child_id,
      });
      ward && wardCode && setWard({ name: ward, code: wardCode });
      setMounted(true);
    }
    async function setDefaultBase() {
      const provincesData = await getProvinces();
      const dfProvince=provincesData[0];
      const districtsData = await getDistricts(dfProvince.child_id);
      const dfDistrict=districtsData[0];
      const wardsData = await getWards(districtsData[0].child_id);
      const dfWard=wardsData[0];
      setProvinces(provincesData);
      setDistricts(districtsData);
      setWards(wardsData);
      setProvince(dfProvince);
      setDistrict(dfDistrict);
      setWard(dfWard);
      setMounted(true);
    }
    if(showDefault){
      setDefault();
    }else{
      setDefaultBase()
    }
  }, [defaultValues, mounted,showDefault]);
  if (loading) {
    return <Loading />;
  }

  return (
    <>
      <FormControl>
        <FormLabel
          ms="4px"
          fontSize="sm"
          fontWeight="500"
          color={textColor}
          display="flex"
        >
          Provinces<Text color={brandStars}>*</Text>
        </FormLabel>
        <Select
          color={textColor}
          mb="10px"
          value={convertIdNameToString(province)}
          onChange={(e) => {
            // setProvince();
            updateProvinces(getIdAndNameLocation(e.target.value));
          }}
          isDisabled={isDisabled}
        >
          {provinces.map((value) => {
            return (
              <option
                value={convertIdNameToString(value)}
                name-data={value.name}
                color={textColor}
              >
                {value.name}
              </option>
            );
          })}
        </Select>
      </FormControl>
      {loadingDistrict ? (
        <Loading />
      ) : (
        <FormControl>
          <FormLabel
            ms="4px"
            fontSize="sm"
            fontWeight="500"
            color={textColor}
            display="flex"
          >
            Districts<Text color={brandStars}>*</Text>
          </FormLabel>
          <Select
            color={textColor}
            mb="10px"
            value={convertIdNameToString(district)}
            onChange={(e) => {
              setDistrict(getIdAndNameLocation(e.target.value));
              updateWards(getIdAndNameLocation(e.target.value));
            }}
            isDisabled={isDisabled}
          >
            {districts.map((value) => {
              return (
                <option
                  value={convertIdNameToString(value)}
                  name-data={value.name}
                  color={textColor}
                >
                  {value.name}
                </option>
              );
            })}
          </Select>
        </FormControl>
      )}
      {loadingWard ? (
        <Loading />
      ) : wards.length > 0 ? (
        <FormControl>
          <FormLabel
            ms="4px"
            fontSize="sm"
            fontWeight="500"
            color={textColor}
            display="flex"
          >
            Wards<Text color={brandStars}>*</Text>
          </FormLabel>
          <Select
            color={textColor}
            mb="10px"
            value={convertIdNameToString(ward,false)}
            onChange={(e) => {
              setWard(getIdAndNameLocation(e.target.value,false));
            }}
            isDisabled={isDisabled}
          >
            {wards.map((value) => {
              return (
                <option
                  value={convertIdNameToString(value,false)}
                  name-data={value.name}
                  color={textColor}
                >
                  {value.name}
                </option>
              );
            })}
          </Select>
        </FormControl>
      ) : (
        <></>
      )}
    </>
  );
};

export default memo(Location);
