import {AdapterDateFns} from "@mui/x-date-pickers/AdapterDateFns";
import {Stack} from "@mui/material";
import TextField from '@mui/material/TextField';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { MobileDatePicker } from '@mui/x-date-pickers/MobileDatePicker';

type EditDateOfArrivalProps = {
    newDateOfArrival : Date
    setNewDateOfArrival: (newDateOfArrival: Date) => void
}

export default function EditDateOfArrival({newDateOfArrival, setNewDateOfArrival}: EditDateOfArrivalProps) {
    return (
        <div className="to-change">
            <p>When are you planning to go?</p>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
                <Stack spacing={3}>
                    <MobileDatePicker
                        label="Date desktop"
                        inputFormat="yyyy-MM-dd"
                        value={newDateOfArrival}
                        onChange={event => event && setNewDateOfArrival(event)}
                        renderInput={(params) => <TextField {...params} />}
                    />
                </Stack>
            </LocalizationProvider>
        </div>
    )
}