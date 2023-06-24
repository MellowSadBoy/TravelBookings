import { initializeApp } from "firebase/app";
import { getDatabase } from "firebase/database";
import {
  FacebookAuthProvider,
  getAuth,
  GoogleAuthProvider,
} from "firebase/auth";
import { getStorage } from "firebase/storage";

const firebaseConfig = {
  apiKey: "AIzaSyB_LpinZKjCBz_6sFMDizPl7bHLMxKFwaM",
  authDomain: "cdda-f9cd6.firebaseapp.com",
  projectId: "cdda-f9cd6",
  storageBucket: "cdda-f9cd6.appspot.com",
  messagingSenderId: "206374805131",
  appId: "1:206374805131:web:3a5f5e3078b1d39cbbbe8e",
  measurementId: "G-XF4NNY5FZ9",
};

export const app = initializeApp(firebaseConfig);

export const auth = getAuth(app);

export const db = getDatabase(app);

export const storage = getStorage(app);
export const provider = new GoogleAuthProvider();
export const providerFB = new FacebookAuthProvider();
