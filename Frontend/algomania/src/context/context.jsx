import { createContext, useContext, useEffect, useState } from "react";
import axios from "axios";
import { BASE_URL } from "../constant"; 
// Create Auth Context
const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(null); // Null = loading state

    // Function to check if user is authenticated
    const checkAuthStatus = async () => {
        console.log("🔄 Checking authentication status...");

        try {
            const response = await axios.get(`${BASE_URL}/authv1/test`, {
                withCredentials: true, // Ensures cookies are sent with the request
            });

            console.log("✅ Response received from backend:", response.data);

            // Backend directly returns true/false, so we set it as is
            setIsAuthenticated(response.data);
        } catch (error) {
            console.error("🚨 Error while checking auth status:", error);
            setIsAuthenticated(false);
        }
    };

    // 🔥 Check authentication on every page load
    useEffect(() => {
        checkAuthStatus();
    }, []);

    return (
        <AuthContext.Provider value={{ isAuthenticated, checkAuthStatus }}>
            {children}
        </AuthContext.Provider>
    );
};

// Hook to use Auth Context
export const useAuth = () => useContext(AuthContext);
