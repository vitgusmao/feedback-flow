import { useEffect, useState } from "react"
import { useFetch } from "../../hooks/useFetchAxios";
import Feedback from "./Feedback";
import Filter from "./Filter";
import Form from "./Form";
import { Scrollable } from "./styled";

import Context from "./Context";

import _ from "lodash";

const Topic = () => {
    const [feedbacks, setFeedbacks] = useState([]);
    const [statusFeedback, setStatusFeedback] = useState();
    const [typeFeedback, setTypeFeedback] = useState();

    const getFeedbacks = () => {
        useFetch({ path: `/history` }).then(response => {
            if (response.data) {
                setFeedbacks(response.data)
            } else {
                setFeedbacks([]);
            }
        }).catch(err => {

        })
    }

    useEffect(_ => {
        getFeedbacks();
        setInterval(getFeedbacks, 2000);
    }, [])

    return (
        <>
            <Context.Provider value={
                {
                    getFeedbacks,
                    typeFeedback,
                    setTypeFeedback,
                    statusFeedback,
                    setStatusFeedback
                }
            }>
                <Filter />
                <Scrollable style={{textAlign: "center"}}>
                    {_.filter(feedbacks, feedback => {
                        if (statusFeedback) {
                            if (feedback.status !== statusFeedback) return false
                        }

                        if (typeFeedback) {
                            if (feedback.type !== typeFeedback) return false
                        }

                        return true;
                    }).map(feedback => <Feedback 
                        key={Math.random()} 
                        getFeedbacks={getFeedbacks} 
                        feedback={feedback} />)}
                </Scrollable>
                <Form />
            </Context.Provider>
        </>
    )
}

export default Topic