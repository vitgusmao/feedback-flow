import Autocomplete from "@mui/material/Autocomplete";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField"
import { useContext } from "react"

import Context from "../Context";

const Filter = _ => {
    const {
        statusFeedback,
        setStatusFeedback,
        typeFeedback,
        setTypeFeedback
    } = useContext(Context)

    const optionsTopic = [
        {
            label: 'Elogio',
            id: "COMPLIMENT"
        }, {
            label: 'Sugestão',
            id: "SUGGESTION"
        }, {
            label: 'Critica',
            id: "CRITIQUE"
        },
    ];

    const optionsStatus = [
        {
            label: 'Recebido',
            id: "RECEIVED"
        }, {
            label: 'Processando',
            id: "PROCESSING"
        }, {
            label: 'Finalizado',
            id: "FINISHED"
        },
    ];

    return (
        <Box
            component="form"
            sx={{
                '& > :not(style)': { m: 1, width: '25ch' },
                display: "inline"
            }}
            noValidate
            autoComplete="off">
            <Autocomplete
                disablePortal
                onChange={(e, newValue) => setStatusFeedback(_ => newValue?.id)}
                options={optionsStatus}
                style={{ display: "inline-flex", width: "48%" }}
                renderInput={(params) => <TextField {...params} label="Situação" />}
            />
            <Autocomplete
                disablePortal
                onChange={(e, newValue) => setTypeFeedback(_ => newValue?.id)}
                options={optionsTopic}
                style={{ display: "inline-flex", width: "48%" }}
                renderInput={(params) => <TextField {...params} label="Topico" />}
            />
        </Box>
    )
}

export default Filter;