import {useParams} from 'react-router-dom';
import { useAuth } from './AuthContext';

export function withParams(Component) {
    return props => <Component {...props} params={useParams()} />;
}


export function withAuth(WrappedComponent) {
    return (props) => {
        const auth = useAuth();
        return <WrappedComponent {...props} auth={auth} />;
    };
};
