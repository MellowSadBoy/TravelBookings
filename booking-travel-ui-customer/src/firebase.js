// Import the functions you need from the SDKs you need

import { initializeApp } from "firebase/app";

import { getAnalytics } from "firebase/analytics";

import {getStorage} from "firebase/storage";

import {getAuth,GoogleAuthProvider} from "firebase/auth";
const firebaseConfig = {

    apiKey: "AIzaSyCB59vEhJHeDMoV3GQodZmfIFMlmQOqngE",

    authDomain: "booking-travel-71400.firebaseapp.com",

    projectId: "booking-travel-71400",

    storageBucket: "booking-travel-71400.appspot.com",

    messagingSenderId: "789822774189",

    appId: "1:789822774189:web:f2d1f80a0278762cb91f24",

    measurementId: "G-5L7N2ND9BY"

};


// Initialize Firebase

const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const auth = getAuth(app);
const ggProvider = new GoogleAuthProvider();
export const storage = getStorage(app);
export {auth, ggProvider};