import moment from 'moment';

const hourFormatter = new Intl.DateTimeFormat('en-GB', { 
  hour: '2-digit', 
  minute: '2-digit' 
})

const fullFormatter = new Intl.DateTimeFormat('en-GB', { 
  hour: '2-digit', 
  minute: '2-digit',
  year: '2-digit',
  month: '2-digit', 
  day: '2-digit' 
})

const dayFormatter = new Intl.DateTimeFormat('en-GB', { 
  weekday: 'short',
  month: '2-digit', 
  day: '2-digit' 
})

export function doSomething() {
  return 'something';
}

export function formatHours(date) {
  return hourFormatter.format(date)
}

export function formatFull(date) {
  return fullFormatter.format(date)
}

export function formatDay(date) {
  return dayFormatter.format(date)
}


export function calculateDuration(segment){


  var durationMinutes = Math.round((segment.endDateObj.format('x') - segment.startDateObj.format('x')) / (1000 * 60));

  var days = Math.floor(durationMinutes / (24 * 60))
  var remainingMinutes = durationMinutes - (days * 24 * 60)
  var hours = Math.floor(remainingMinutes / 60)
  remainingMinutes = remainingMinutes - (hours * 60)
  var minutes = remainingMinutes

  var durationString = ""
  if (days > 0) {
    durationString += days + "d "
  }

  if (days > 0 || hours > 0) {
    durationString += hours + "h "
  }

  durationString += minutes + "min"

  return durationString
}

export function convertStringsToDateObjects(segment) {

  var timeFormat = "YYYY-MM-DD'T'HH:mm:ss.SSSZ"

  var startDate = segment.startDate
  segment.startDateObj = moment.utc(startDate, timeFormat)
  delete segment.startDate

  var endDate = segment.endDate
  segment.endDateObj = moment.utc(endDate, timeFormat)
  delete segment.endDate

  if(segment.type === "datesegment"){
    segment.activities.forEach(activity => {
      convertStringsToDateObjects(activity)
    })
  }
}

export function convertDateObjectsToStrings(segment) {

  var timeFormat = "YYYY-MM-DDTHH:mm:ss.SSSZ"

  segment.startDate = segment.startDateObj.format(timeFormat);
  segment.endDate = segment.endDateObj.format(timeFormat);

  if(segment.type === "datesegment"){
    segment.activities.forEach(activity => {
      convertDateObjectsToStrings(activity)
    })
  }
}