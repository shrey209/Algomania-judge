import { createContext, useContext, useEffect, useState } from "react";

// Create Auth Context
const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(null); // Null = loading state

    // Function to check if user is authenticated
    const checkAuthStatus = async () => {
        console.log("🔄 Checking authentication status...");

        try {
            const response = await fetch("http://localhost:8000/authv1/test", {
                credentials: "include", // Ensures cookies are sent with the request
            });

            console.log("✅ Response received from backend:", response);

            if (response.ok) {
                console.log("✅ User is authenticated!");
                setIsAuthenticated(true);
            } else {
                console.log("❌ User is NOT authenticated!");
                setIsAuthenticated(false);
            }
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
