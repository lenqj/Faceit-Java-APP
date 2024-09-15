import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import UserHome from "pages/user/UserHome";
import UserRegister from "pages/user/UserRegister";
import UserUpdate from "pages/user/UserUpdate";
import UserProfile from "pages/user/UserProfile";
import Login from "pages/user/Login";
import Logout from "pages/user/Logout";
import MatchHome from "pages/match/MatchHome";
import HorizontalMenu from "pages/menu/HorizontalMenu";
import MatchProfile from "pages/match/MatchProfile";
import MatchCreate from "pages/match/MatchCreate"
import UserComparisonPage from "pages/user/UserCompare";
import TeamHome from "pages/team/TeamHome";
import TeamProfile from "pages/team/TeamProfile";
import TeamCreate from "pages/team/TeamCreate";
import Support from "pages/support/Support";
import ConfirmTokenPage from "pages/token/ConfirmTokenPage";
import UserHistory from "pages/history/UserHistory";
import UserSteamAccounts from "pages/user/UserSteamAccounts";
import { AuthProvider } from "utils/AuthContext";



function App() {
  return (
    <AuthProvider>
      <Router>
      <HorizontalMenu />
        <Routes>
          <Route exact path="/login" element={<Login/>}/>
          <Route exact path="/users" element={<UserHome/>}/>
            <Route exact path="/register" element={<UserRegister/>}/>
            <Route exact path="/profile/:username?" element={<UserProfile />}/>
            <Route exact path="/profile/edit" element={<UserUpdate/>}/>
            <Route exact path="/compare/:username" element={<UserComparisonPage/>}/>
            <Route exact path="/logout" element={<Logout/>}/>
            <Route exact path="/steam-accounts" element={<UserSteamAccounts/>}/>         
          <Route exact path="/matches" element={<MatchHome/>}/>
            <Route exact path="/match/:name?" element={<MatchProfile/>}/>
            <Route exact path="/match/create" element={<MatchCreate/>}/>
          <Route exact path="/teams" element={<TeamHome/>}/>
            <Route exact path="/team/:name?" element={<TeamProfile/>}/>
            <Route exact path="/team/create" element={<TeamCreate/>}/>

          <Route exact path="/support" element={<Support/>}/>
          <Route exact path="/confirm-account/:token?" element={<ConfirmTokenPage/>}/>
          <Route exact path="/history" element={<UserHistory/>}/>       


        </Routes>
      
      </Router>
      </AuthProvider>
  );
}

export default App;
