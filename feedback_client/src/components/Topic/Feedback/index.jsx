import Alert from "@mui/material/Alert"
import Card from "@mui/material/Card"
import CardContent from "@mui/material/CardContent"
import Typography from "@mui/material/Typography"

const Feedback = ({feedback}) => {
    return(
        <>
            <Card key={Math.random()} variant="outlined" sx={{width: "40%"}} style={{textAlign: "center"}}>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {feedback.type === 'COMPLIMENT' && (<Alert severity="success">Elogio</Alert>)}
                        {feedback.type === 'CRITIQUE' && (<Alert severity="error">Critica</Alert>)}
                        {feedback.type === 'SUGGESTION' && (<Alert severity="info">Sugestão</Alert>)}
                    </Typography>
                    <Typography gutterBottom variant="body1" component="div">
                        {feedback.message}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {feedback.status}
                    </Typography>
                </CardContent>
            </Card>
            <br />
            <br />
        </>
    )
}

export default Feedback