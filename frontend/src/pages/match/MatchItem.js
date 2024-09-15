import React from "react";
import { TableCell, TableRow } from "@mui/material";

class MatchItem extends React.Component {
    render() {
        const { match } = this.props;
        const encodedName = match.name.replace(/ /g, "%20");

        return (
            <TableRow hover onClick={() => window.location.href="/match/" + encodedName}>
                <TableCell>
                    {match.startTimeMatch}
                </TableCell>
                <TableCell>
                    {match.name}
                </TableCell>
                <TableCell>{match.teamAScore.finalScore + " - " + match.teamBScore.finalScore}</TableCell>
                <TableCell>{match.map}</TableCell>
            </TableRow>
        );
    }
}

export default MatchItem;
