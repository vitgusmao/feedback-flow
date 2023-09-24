import Autocomplete from "@mui/material/Autocomplete";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField"

import { useContext } from "react";
import { useState } from "react"

import Swal from "sweetalert2";
import { useFetch } from "../../../hooks/useFetchAxios";
import Context from "../Context";

const Form = _ => {
    const [message, setMessage] = useState("");
    const [type, setType] = useState("")

    const {
        getFeedbacks
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

    const handleChangeMessage = e => {
        setMessage(e.target.value);
    }

    const handleClick = _ => {
        if (message.length <= 0) {
            Swal.fire({
                icon: 'error',
                text: 'Digite uma mensagem'
            });
            return;
        }

        if (!type) {
            Swal.fire({
                icon: 'error',
                text: 'Selecione um topico'
            });
            return;
        }
        const data = {
            message,
            type
        };

        useFetch({ path: '', method: 'post', data })
            .then(res => {
                getFeedbacks();
            })
            .catch(_ => {
                console.log('Error')
            });
    }

    return (
        <Box
            component="form"
            sx={{
                '& > :not(style)': { m: 1, width: '25ch' },
                display: "inline"
            }}
            noValidate
            autoComplete="off">
            <TextField
                value={message}
                onChange={handleChangeMessage}
                label={"Mensagem"}
                style={{ width: "50%" }}
            />
            <Autocomplete
                disablePortal
                onChange={(e, newValue) => setType(newValue?.id)}
                options={optionsTopic}
                style={{ display: "inline-flex", width: "25%" }}
                renderInput={(params) => <TextField {...params} label="Topico" />}
            />
            <Button
                onClick={handleClick}
                variant="outlined"
                size="large"
                style={{ width: "20%" }}
            >Publish Message</Button>
        </Box>
    )
}

export default Form;