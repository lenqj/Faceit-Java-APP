import React from 'react';
import { useAuth } from 'contexts/AuthContext';

const withAuth = (WrappedComponent) => {
    return (props) => {
        const auth = useAuth();
        return <WrappedComponent {...props} auth={auth} />;
    };
};

export default withAuth;