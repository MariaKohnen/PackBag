import {AdapterDateFns} from "@mui/x-date-pickers/AdapterDateFns";
import {Stack} from "@mui/material";
import TextField from '@mui/material/TextField';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { MobileDatePicker } from '@mui/x-date-pickers/MobileDatePicker';
import "./EditDateOfArrival.css";

type EditDateOfArrivalProps = {
    newDateOfArrival : Date
    setNewDateOfArrival: (newDateOfArrival: Date) => void
}

export default function EditDateOfArrival({newDateOfArrival, setNewDateOfArrival}: EditDateOfArrivalProps) {

    const today = new Date();
    const date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();

    return (
        <div className="to-change">
            <p>When are you planning to go?</p>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
                <Stack className="date-picker-field" spacing={3}>
                    <MobileDatePicker
                        inputFormat="dd-MM-yyyy"
                        value={newDateOfArrival? newDateOfArrival : new Date(date)}
                        InputProps={{disableUnderline: true}}
                        onChange={event => event && setNewDateOfArrival(event)}
                        renderInput={(params) => <TextField {...params} variant={"standard"} />}
                    />
                </Stack>
            </LocalizationProvider>
        </div>
    )
}

