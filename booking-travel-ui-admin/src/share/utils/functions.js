import moment from "moment/moment";
import { EMAIL_REGREX } from "share/data/constant";
import { VehicleTypes } from "share/data/data";
import { RoomType } from "share/data/data";
import { RankHotelType } from "share/data/data";
import { TourbookedStatus } from "share/data/data";
import { HotelbookedStatus } from "share/data/data";
import { onValue, ref, update } from "firebase/database";
import {
  deleteObject,
  getDownloadURL,
  ref as refStorage,
  uploadBytes,
} from "firebase/storage";
import { storage } from "share/firebase";

export const getIdAndNameLocation = (value, status = true) => {
  if (!value)
    return {
      code: "",
      name: "",
    };
  const arr = value.split("_");
  arr[0] = arr[0] * 1;
  if (status)
    return {
      code: arr[0],
      name: arr[1],
      child_id: arr[2],
    };
  return {
    code: arr[0],
    name: arr[1],
  };
};

export const convertIdNameToString = (obj, status = true) => {
  if (!obj.code || !obj.name) return "";
  if (status) {
    return obj.code + "_" + obj.name + "_" + obj.child_id;
  }
  return obj.code + "_" + obj.name;
};

export const getSeats = (vehicleType) => {
  return VehicleTypes.filter((vl) => vl.hint === vehicleType)[0].seats;
};

export const getTimeConvert = (value) => {
  return moment(value).format("dddd, MMMM Do YYYY");
};

export const getDateTime = (value) => {
  return new Date(value).getTime();
};

export const getTimeInputFormat = (value) => {
  return moment(value).format("YYYY-MM-DD");
};

export const isValidEmail = (email) => {
  return EMAIL_REGREX.test(email);
};

export const encodeBase64 = (string) => {
  let encodedString = btoa(string);
  return encodedString;
};

export const isFileImage = (file) => {
  const acceptedImageTypes = ["image/gif", "image/jpeg", "image/png"];

  return file && acceptedImageTypes.includes(file["type"]);
};

export const uploadImageFB = async (file, nameF) => {
  const nameFile = nameF + ".png";
  const storageRef = refStorage(storage, nameFile);
  await uploadBytes(storageRef, file).then(() => {});
  return await getDownloadURL(storageRef, nameFile)
    .then((url) => {
      const xhr = new XMLHttpRequest();
      xhr.responseType = "blob";
      xhr.onload = () => {};
      xhr.open("GET", url);
      xhr.send();
      return url;
    })
    .catch(() => {
      return null;
    });
};

export const deleteImageFB = async (file, nameF) => {
  const desertRef = ref(storage, file);
  return deleteObject(desertRef)
    .then(() => {
      return true;
    })
    .catch((error) => {
      return false;
    });
};

export const guidGenerator = () => {
  var S4 = function () {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
  };
  return (
    S4() +
    S4() +
    "-" +
    S4() +
    "-" +
    S4() +
    "-" +
    S4() +
    "-" +
    S4() +
    S4() +
    S4()
  );
};

export const getRoomSeats = (rooomType) => {
  return RoomType.filter((vl) => vl.hint === rooomType)[0].numberOfSeat;
};

export const getStart = (rankHotelType) => {
  return RankHotelType.filter((vl) => vl.hint === rankHotelType)[0].title;
};

export const getTypeOfTourbooked = (tourbookedStatus) => {
  return TourbookedStatus.filter((vl) => vl.hint === tourbookedStatus)[0].title;
};

export const getTypeOfHotelbooked = (hotelbookedStatus) => {
  return HotelbookedStatus.filter((vl) => vl.hint === hotelbookedStatus)[0]
    .title;
};
