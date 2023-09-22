import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import Topic from './components/Topic'
import Container from '@mui/material/Container'
import { Card } from '@mui/material'


ReactDOM.createRoot(document.getElementById('root')).render(
    <Container>
        <Card sx={{padding: 5}}>
            <Topic />
        </Card>
    </Container>
)
