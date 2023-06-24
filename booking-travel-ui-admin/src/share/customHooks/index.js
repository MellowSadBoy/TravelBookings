import { useMemo } from "react";
import { useSelector } from "react-redux";

export const useCheckIsSignin = () => {
  const user = useSelector((state) => state.auth.user);
  const token = useSelector((state) => state.auth.token);

  const isSignIn = useMemo(() => {
    if (!user || !token) {
      return false;
    }
    return true;
  }, [user, token]);
  return isSignIn;
};
